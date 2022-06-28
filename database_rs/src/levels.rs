use std::mem::MaybeUninit;
use std::sync::{Mutex, Once};
use j4rs::{Instance, InvocationArg, Jvm};
use redis::{Commands, Connection, RedisError};
use j4rs_derive::*;
use j4rs::prelude::*;

#[call_from_java("org.dyrka.sanitar.database.util.Startup.createLevelDatabase")]
fn create_database() {
    let mut con = singleton().inner.lock().expect("Can't get connection instance");

    let result: () = con.hset("levels", "0", "0").expect("Can't create hashmap.");
}

#[call_from_java("org.dyrka.sanitar.database.level.LevelDataBase.getLevelRs")]
fn get_level(id: Instance) -> Result<Instance, RedisError> {
    let mut con = singleton().inner.lock().expect("Can't get connection instance");

    let jvm = Jvm::attach_thread().expect("Can't create jvm.");

    let level: i64 = match con.hget("levels", jvm.to_rust::<i64>(id).expect("Can't convert jvm int to rust i64")) {
        Ok(integer) => integer,
        Err(error) => -5
    };
    let invc_arg = InvocationArg::try_from(level).expect("Failed to convert object to jvm.");
    return Ok(Instance::try_from(invc_arg).expect("Failed to convert rust obejct to jvm object."));
}

#[call_from_java("org.dyrka.sanitar.database.level.LevelDataBase.setLevelRs")]
fn set_level(id: Instance, level: Instance) {
    let mut con = singleton().inner.lock().expect("Can't get connection instance");

    let jvm = Jvm::attach_thread().expect("Can't create jvm.");

    let result: () = con.hset("levels", jvm.to_rust::<i64>(id).expect("Can't convert id to rust.").to_string().as_str(), jvm.to_rust::<i64>(level).expect("Can't convert level to rust.").to_string().as_str()).expect("Can't set member level.");
}

struct SingletonReader {
    inner: Mutex<Connection>,
}

fn singleton() -> &'static SingletonReader {
    static mut SINGLETON: MaybeUninit<SingletonReader> = MaybeUninit::uninit();
    static ONCE: Once = Once::new();

    unsafe {
        ONCE.call_once(|| {
            let singleton = SingletonReader {
                inner: Mutex::new(redis::Client::open("redis://127.0.0.1").expect("Can't connect to redis").get_connection().expect("Can't create connection to redis")),
            };
            SINGLETON.write(singleton);
        });

        SINGLETON.assume_init_ref()
    }
}
use std::mem::MaybeUninit;
use std::sync::{Mutex, Once};
use redis::Connection;

mod levels;

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
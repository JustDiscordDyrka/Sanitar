use std::fmt::{Debug, Formatter};
use std::sync::{Mutex, Once};
use j4rs::{Instance, InvocationArg, Jvm};
use redis::{Commands, Connection, RedisError};
use j4rs_derive::*;
use j4rs::prelude::*;
use crate::singleton;

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

#[call_from_java("org.dyrka.sanitar.database.level.LevelDataBase.getXpRs")]
fn get_xp(id: Instance) -> Result<Instance, RedisError> {
    let mut con = singleton().inner.lock().expect("Can't get connection instance");

    let jvm = Jvm::attach_thread().expect("Can't create jvm.");

    let xp: i64 = match con.hget("xp", jvm.to_rust::<i64>(id).expect("Can't convert jvm int to rust i64")) {
        Ok(integer) => integer,
        Err(error) => -5
    };
    let invc_arg = InvocationArg::try_from(xp).expect("Failed to convert object to jvm.");
    return Ok(Instance::try_from(invc_arg).expect("Failed to convert rust obejct to jvm object."));
}

#[call_from_java("org.dyrka.sanitar.database.level.LevelDataBase.setLevelRs")]
fn set_level(id: Instance, level: Instance) {
    let mut con = singleton().inner.lock().expect("Can't get connection instance");

    let jvm = Jvm::attach_thread().expect("Can't create jvm.");

    let result: () = con.hset("levels", jvm.to_rust::<i64>(id).expect("Can't convert id to rust.").to_string().as_str(), jvm.to_rust::<i64>(level).expect("Can't convert level to rust.").to_string().as_str()).expect("Can't set member level.");
}

#[call_from_java("org.dyrka.sanitar.database.level.LevelDataBase.setXpRs")]
fn set_xp(id: Instance, xp: Instance) {
    let mut con = singleton().inner.lock().expect("Can't get connection instance");

    let jvm = Jvm::attach_thread().expect("Can't create jvm.");

    let result: () = con.hset("xp", jvm.to_rust::<i64>(id).expect("Can't convert id to rust.").to_string().as_str(), jvm.to_rust::<i64>(xp).expect("Can't convert level to rust.").to_string().as_str()).expect("Can't set member level.");
}
package com.project.utils;

import java.util.UUID;

public class UUIDLong {
	public static long longUUID() {
		return UUID.randomUUID().getMostSignificantBits();
	}

	public static long absLongUUID() {
		long r;
		do
			r = longUUID();
		while (r <= 0L);
		return r;
	}
}

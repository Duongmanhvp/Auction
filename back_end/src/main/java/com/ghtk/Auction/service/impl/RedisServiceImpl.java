package com.ghtk.Auction.service.impl;

import com.ghtk.Auction.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
	private final RedisTemplate<String,String> redisTemplate;
	@Autowired
	public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
//	@Override
//	public void saveRedis(String key, String value) {
//		redisTemplate.opsForValue().set(key, value, 3, TimeUnit.MINUTES);
//	}
}

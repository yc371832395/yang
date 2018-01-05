package com.mdm.session;

import java.util.List;

public interface ISessionCache {
	void put(TokenSession session) throws Exception;

	void remove(String token) throws Exception;

	TokenSession get(String token) throws Exception;

	List<TokenSession> list() throws Exception;

	void clear() throws Exception;

	Integer size() throws Exception;
}

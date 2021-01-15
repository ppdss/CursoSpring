package com.curso.spring.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	// static porque posso chamar esse método sem instanciar a classe
	public static List<Integer> decodeIntList(String s){
		String [] vet = s.split(",");
		List<Integer> list = new ArrayList<>();	
		for(int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		// utilizando o lâmbida para fazer esse método em uma só linha
		// return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

	public static String decodeParam(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			return "";
		}
	}
}

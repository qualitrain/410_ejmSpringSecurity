package mx.com.qtx.ejmSpSec.seguridad.util;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import mx.com.qtx.ejmSpSec.seguridad.servicios.IGeneradorTokensJWT;

public class TestIGeneradorTokens {

	public static void main(String[] args) {
		IGeneradorTokensJWT generadorTokens = new GeneradorTokensJWT_JJWT0_12_6_SHA512();
		
		test_CreacionTokenJWT(generadorTokens);
		test_CreacionTokenJWTconClaimsPersonalizados(generadorTokens);
		test_ParserTokenFirmado(generadorTokens);
		test_ExtraerDatos(generadorTokens);
		test_tokenExpirado(generadorTokens);
	}
	
	private static void test_CreacionTokenJWT(IGeneradorTokensJWT generadorTokens){
		System.out.println("\n*** test_CreacionTokenJWT() ***");
		String token = generadorTokens.generarToken("alex");
		System.out.println("Token:" + token);		
		System.out.println("llave en Base 64:" + generadorTokens.getLlaveBase64());
	}

	private static void test_CreacionTokenJWTconClaimsPersonalizados(IGeneradorTokensJWT generadorTokens){
		System.out.println("\n*** test_CreacionTokenJWTconMap() ***");
		Map<String,Object> mapClaims = new HashMap<>();
		mapClaims.put("rol","Agente");
		mapClaims.put("permisos", "0777");
		String token = generadorTokens.generarToken("beto",mapClaims);
		System.out.println("Token:" + token);
		System.out.println("llave en Base 64:" + generadorTokens.getLlaveBase64());
	}
	
	private static void test_ParserTokenFirmado(IGeneradorTokensJWT generadorTokens) {
		System.out.println("\n*** test_ParserTokenFirmado() ***");
		
		Map<String,Object> mapClaims = new HashMap<>();
		mapClaims.put("rol","Administrador");
		mapClaims.put("permisos", "0777");
		String token = generadorTokens.generarToken("mortadelo", mapClaims);
		
		String resultado = generadorTokens.extraerContenidoTokenFirmadoStr(token);
		System.out.println(resultado);
		
		String rol = generadorTokens.extraerCampo(token,String.class, "rol");
		System.out.println("Rol recuperado del token:" + rol);
	}

	private static void test_ExtraerDatos(IGeneradorTokensJWT generadorTokens) {
		System.out.println("\n*** test_ExtraerDatos() ***");
		
		Map<String,Object> mapClaims = new HashMap<>();
		mapClaims.put("rol","Administrador");
		mapClaims.put("permisos", "0777");
		String token = generadorTokens.generarToken("mortadelo", mapClaims);
		
		String usuario =  generadorTokens.extraerUsuario(token);
		String rol =      generadorTokens.extraerCampo(token, String.class , "rol" );
		String permisos = generadorTokens.extraerCampo(token, "permisos".getClass(),"permisos");
		System.out.println("Usuario:" + usuario);
		System.out.println("Rol:" + rol);
		System.out.println("Permisos:" + permisos);
	}
	
	private static void test_tokenExpirado(IGeneradorTokensJWT generadorTokens) {
		System.out.println("\n*** test_tokenExpirado() ***");
		
		Map<String,Object> mapClaims = new HashMap<>();
		mapClaims.put("rol","Administrador");
		mapClaims.put("permisos", "0777");
		String tokenOk = generadorTokens.generarToken("mortadelo", mapClaims);
		String tokenExpirado = generadorTokens.generarToken("filemon", mapClaims, -100);
		
		if(generadorTokens.tokenExpirado(tokenExpirado)) {
			System.out.println("El token de filemon está expirado");
		}
		else {
			String usuario = generadorTokens.extraerUsuario(tokenExpirado);
			System.out.println("Todo ok con el token de " + usuario);
		}
		
		if(generadorTokens.tokenExpirado(tokenOk)) {
			System.out.println("El token de mortadelo está expirado");
		}
		else {
			String usuario = generadorTokens.extraerUsuario(tokenOk);
			System.out.println("todo ok con el token de " + usuario);
		}
		
	}	

}

package com.curso.spring.servicies;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.curso.spring.servicies.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Client;


	@Value("${s3.bucket}")
	private String bucketName;

	// tipo MultipartFile é o tipo que o Spring usa para guardar arquivos na requisição
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename(); // extrai nome do arquivo enviado
			InputStream	is = multipartFile.getInputStream(); // encapsula o processamento de leitura a partir de uma origem (nesse caso um arquivo)
			// objeto basico de leitura capaz de encapsular leitura a partir de uma origem
			String contentType = multipartFile.getContentType(); // obtendo tipo do arquivo (png, doc ...)
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: "+ e.getMessage());
		}	
	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("Iniciando upload");
			s3Client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload efetuado com sucesso");
			return s3Client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}
	// localFilePath responsavel por fazer o upload pro s3 a partir de um caminho String de um arquivo
	public void uploadFile(String localFilePath) {
		try {
			File file = new File(localFilePath);
			s3Client.putObject(new PutObjectRequest(bucketName, "teste.png", file));
			LOG.info("Upload efetuado com sucesso");
		}catch(AmazonServiceException e) {
			LOG.info("AmazonServiceException: " + e.getErrorMessage());
			LOG.info("Status code: "+ e.getErrorCode());
		}catch(AmazonClientException e) {
			LOG.info("AmazonClientException: " + e.getMessage());
		}
	}
	
}

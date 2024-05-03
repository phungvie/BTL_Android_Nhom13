package viet.spring.SonicServer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/data")
public class dataController {
	@GetMapping(value = "/img/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImg(@PathVariable String filename) throws IOException {
//		Resource resource = new ClassPathResource("static/" + filename);

		// Đọc dữ liệu từ tài nguyên và trả về trong ResponseEntity
//		byte[] data = Files.readAllBytes(resource.getFile().toPath());
		
		byte[] data = Files.readAllBytes(Paths.get("/dataSonic/img/"+filename));

		return ResponseEntity.ok().body(data);

	}
	
	@GetMapping(value = "/stream/{filename:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getStream(@PathVariable String filename) throws IOException {
//		Resource resource = new ClassPathResource("static/" + filename);

		// Đọc dữ liệu từ tài nguyên và trả về trong ResponseEntity
//		byte[] data = Files.readAllBytes(resource.getFile().toPath());
		byte[] data = Files.readAllBytes(Paths.get("/dataSonic/stream/"+filename));

		return ResponseEntity.ok().body(data);

	}
	
	   private static final String UPLOAD_DIR_IMG = "/dataSonic/img/";
	   private static final String UPLOAD_DIR_STREAM= "/dataSonic/stream/";

	    @PostMapping("/imgUpload")
	    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body("Please upload a file");
	        }
	       
	        try {
	        	
	        	
	            // Tạo một tên mới duy nhất cho tập tin
	            String originalFilename = file.getOriginalFilename();
	            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	            String start = originalFilename.substring(0,originalFilename.lastIndexOf('.'));
	            String newFilename = start+"."+generateUniqueFilename() + extension;
	            
	            
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOAD_DIR_IMG + newFilename);
	            Files.write(path, bytes);
	            return ResponseEntity.ok().body(newFilename);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
	        }
	    }
	    

	    @PostMapping("/streamUpload")
	    public ResponseEntity<String> uploadStream(@RequestParam("file") MultipartFile file) {
	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body("Please upload a file");
	        }

	        try {
	        	
	        	
	            // Tạo một tên mới duy nhất cho tập tin
	            String originalFilename = file.getOriginalFilename();
	            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	            String start = originalFilename.substring(0,originalFilename.lastIndexOf('.'));
	            String newFilename = start+"."+generateUniqueFilename() + extension;
	        	
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOAD_DIR_STREAM + newFilename);
	            Files.write(path, bytes);
	            return ResponseEntity.ok().body(newFilename);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
	        }
	    }
	    
	    private String generateUniqueFilename() {
	        // Sử dụng UUID để tạo tên tập tin duy nhất
	        return UUID.randomUUID().toString();
	    }
	    

	    
}

package viet.spring.SonicServer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import viet.spring.SonicServer.config.Config;
import viet.spring.SonicServer.entity.Role;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.payload.VietMessage;
import viet.spring.SonicServer.repository.ArtistRepository;
import viet.spring.SonicServer.repository.PlaylistRepository;
import viet.spring.SonicServer.repository.RoleRepository;
import viet.spring.SonicServer.repository.SongRepository;
import viet.spring.SonicServer.repository.UserRepository;
import viet.spring.SonicServer.service.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/vnpay")
@AllArgsConstructor
public class VnpayController {
	UserService userService;
	UserRepository userR;
//	PlaylistRepository playlistR;
//	ArtistRepository artistR;
//	SongRepository songR;
	RoleRepository roleRepository;

	@GetMapping("/create_payment")
	public ResponseEntity<?> createPayment(HttpServletRequest req, Principal vietdz) throws UnsupportedEncodingException {
		User viet= userService.findByUsername(vietdz.getName()).orElse(null);
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		//
		String orderType = "other";
		String amount = req.getParameter("amount") + "00";

		String bankCode = req.getParameter("bankCode");

		String vnp_TxnRef = Config.getRandomNumber(8);
		String vnp_IpAddr = Config.getIpAddress(req);

		String vnp_TmnCode = Config.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);

//		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_Amount", amount);

		vnp_Params.put("vnp_CurrCode", "VND");

		if (bankCode != null && !bankCode.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bankCode);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo",viet.getUserID()+"-"+ vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);

		String locate = req.getParameter("language");
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

		return ResponseEntity.ok().body(new VietMessage(00,null,paymentUrl));
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));

		//

	}

//http://localhost:8080/vnpay/return?
//vnp_Amount=10000000000&
//vnp_BankCode=NCB&
//vnp_BankTranNo=VNP14373910&
//vnp_CardType=ATM&
//vnp_OrderInfo=Thanh+toan+don+hang%3A20622293&
//vnp_PayDate=20240411085935&
//vnp_ResponseCode=00&
//vnp_TmnCode=EYY1S4IO&vnp_TransactionNo=14373910&vnp_TransactionStatus=00&vnp_TxnRef=20622293&vnp_SecureHash=3ab9e87ec019f3f42695dbdcf2a2b0121c280ee35eaf65881bd029deaf3bb6e9b69ae63855cd5c08efc2ef82bbcc80ec38e529fada5bd7b437da6cdbdc1ad6e0
	@GetMapping("/return")
	public ResponseEntity<VietMessage> getMethodName(@RequestParam String vnp_Amount, @RequestParam String vnp_BankCode,
			@RequestParam String vnp_BankTranNo, @RequestParam String vnp_CardType, @RequestParam String vnp_OrderInfo,
			@RequestParam String vnp_PayDate, @RequestParam String vnp_ResponseCode) {


		switch (vnp_ResponseCode) {
		
		case "00":
			Integer id=Integer.parseInt(vnp_OrderInfo.split("-")[0]);
			User viet= userR.findById(id).orElse(null);
			Role role=roleRepository.findById(4).orElse(null);
			if(viet!=null&&role!=null) {
				viet.getRoles().add(role);
				userR.save(viet);
			}
			return ResponseEntity
					.ok()
					.body(new VietMessage(0,"Giao dịch thành công"));
		case "01":
			return ResponseEntity
					.ok()
					.body(new VietMessage(1,"Giao dịch chưa hoàn tất"));
		case "02":
			return ResponseEntity
					.ok()
					.body(new VietMessage(2,"Giao dịch bị lỗi"));
		case "04":
			return ResponseEntity
					.ok()
					.body(new VietMessage(4,"Giao dịch đảo (Khách hàng đã bị trừ tiền tại Ngân hàng nhưng GD chưa thành công ở VNPAY)"));
		case "05":
			return ResponseEntity
					.ok()
					.body(new VietMessage(5,"VNPAY đang xử lý giao dịch này (GD hoàn tiền)"));
		case "06":
			return ResponseEntity
					.ok()
					.body(new VietMessage(6,"VNPAY đã gửi yêu cầu hoàn tiền sang Ngân hàng (GD hoàn tiền)"));
		case "07":
			return ResponseEntity
					.ok()
					.body(new VietMessage(7,"Giao dịch bị nghi ngờ gian lận"));
		case "09":
			return ResponseEntity
					.ok()
					.body(new VietMessage(9,"GD Hoàn trả bị từ chối"));
		
		default:
			return ResponseEntity.badRequest().build();
		}
		
		


	}

	

}

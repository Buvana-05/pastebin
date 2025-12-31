package com.example.pastebin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pastebin.model.CreatePasteRequest;
import com.example.pastebin.model.GetPasteResponse;
import com.example.pastebin.model.Paste;
import com.example.pastebin.service.PasteService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/pastes")
@CrossOrigin(origins = "${FRONTEND_URL}")
public class PasteApiController {

	private final PasteService service;

	@Value("${FRONTEND_URL}")
	private String frontendUrl;

	public PasteApiController(PasteService service) {
		this.service = service;
	}

	@GetMapping("/healthz")
	public Map<String, Boolean> health() {
		return Map.of("ok", service.isHealthOk());
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody CreatePasteRequest req) {

		if (req.getContent() == null || req.getContent().trim().isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Content is required"));
		}

		if (req.getTtlSeconds() != null && req.getTtlSeconds() < 1) {
			return ResponseEntity.badRequest().body(Map.of("error", "ttl_seconds must be >= 1"));
		}

		if (req.getMaxViews() != null && req.getMaxViews() < 1) {
			return ResponseEntity.badRequest().body(Map.of("error", "max_views must be >= 1"));
		}

		Paste paste = service.create(req.getContent(), req.getTtlSeconds(), req.getMaxViews());

		String url = frontendUrl + "/p/" + paste.getId();

		return ResponseEntity.ok(Map.of("id", paste.getId().toString(), "url", url));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPaste(@PathVariable String id) {
		try {
			Paste paste = service.get(id);
			GetPasteResponse response = new GetPasteResponse(
					paste.getContent(),
					paste.getRemainingViews(),
					paste.getExpiresAt());

			return ResponseEntity.ok()
					.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
					.header("Pragma", "no-cache")
					.body(response);
		} catch (Exception e) {
			return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
		}
	}
}
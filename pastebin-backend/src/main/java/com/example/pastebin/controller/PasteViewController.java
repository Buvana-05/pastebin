package com.example.pastebin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import com.example.pastebin.model.Paste;
import com.example.pastebin.service.PasteService;

import java.util.UUID;

@Controller
public class PasteViewController {
	private final PasteService service;

	public PasteViewController(PasteService service) {
		this.service = service;
	}

	@GetMapping(value = "/p/{id}", produces = "text/plain")
	public ResponseEntity<String> viewPaste(@PathVariable String id) {
		try {
			Paste paste = service.get(id);
			String safeContent = HtmlUtils.htmlEscape(paste.getContent());
			return ResponseEntity.ok()
					.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
					.header("Pragma", "no-cache")
					.body(safeContent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}

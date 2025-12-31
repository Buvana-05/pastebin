package com.example.pastebin.service;

import org.springframework.stereotype.Service;

import com.example.pastebin.model.Paste;
import com.example.pastebin.repository.PasteRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasteService {

	private final PasteRepository repository;
	private final TimeProvider timeProvider;

	public PasteService(PasteRepository repository, TimeProvider timeProvider) {
		this.repository = repository;
		this.timeProvider = timeProvider;
	}

	public Optional<Paste> findById(String id) {
		return repository.findById(id);
	}

	public void decrementViews(Paste paste) {
		if (paste.getRemainingViews() != null && paste.getRemainingViews() > 0) {
			paste.setRemainingViews(paste.getRemainingViews() - 1);
			repository.save(paste);
		}
	}

	@Transactional
	public Paste create(String content, Integer ttl, Integer maxViews) {

		Paste paste = new Paste();
		paste.setId(UUID.randomUUID().toString());
		paste.setContent(content != null ? content : "");
		paste.setCreatedAt(timeProvider.now());

		paste.setMaxViews(maxViews);
		paste.setRemainingViews(maxViews);
		paste.setViews(0);

		if (ttl != null) {
			paste.setExpiresAt(timeProvider.now().plusSeconds(ttl));
		}

		return repository.save(paste);
	}

	@Transactional
	public Paste get(String id) {

		Paste paste = repository.findByIdWithLock(id).orElseThrow(() -> new RuntimeException("Paste not found"));

		Instant now = timeProvider.now();

		// TTL check
		if (paste.getExpiresAt() != null && now.isAfter(paste.getExpiresAt())) {
			throw new RuntimeException("Paste expired");
		}

		// Max views check
		if (paste.getMaxViews() != null && paste.getViews() >= paste.getMaxViews()) {
			throw new RuntimeException("Paste expired");
		}

		paste.setViews(paste.getViews() + 1);

		if (paste.getRemainingViews() != null) {
			paste.setRemainingViews(paste.getRemainingViews() - 1);
		}

		repository.save(paste);

		return paste;
	}

	public boolean isHealthOk() {
		try {
			repository.count(); // Trivial DB call
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
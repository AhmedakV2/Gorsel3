package com.project.reportmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Eşleşmeyen JSON alanlarını görmezden gelir
public record GitHubUser(String name, String url) {
}
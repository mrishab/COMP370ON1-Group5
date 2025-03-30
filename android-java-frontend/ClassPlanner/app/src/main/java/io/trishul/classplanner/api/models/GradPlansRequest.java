package io.trishul.classplanner.api.models;

import java.util.List;

public class GradPlansRequest {
    private List<Long> ids;
    private List<String> programs;
    private Integer creditsFrom;
    private Integer creditsTo;
    private Double gpaFrom;
    private Double gpaTo;
    private String sortBy;
    
    public GradPlansRequest() {
        // Default constructor
    }
    
    // Getters and setters
    public List<Long> getIds() {
        return ids;
    }
    
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
    
    public List<String> getPrograms() {
        return programs;
    }
    
    public void setPrograms(List<String> programs) {
        this.programs = programs;
    }
    
    public Integer getCreditsFrom() {
        return creditsFrom;
    }
    
    public void setCreditsFrom(Integer creditsFrom) {
        this.creditsFrom = creditsFrom;
    }
    
    public Integer getCreditsTo() {
        return creditsTo;
    }
    
    public void setCreditsTo(Integer creditsTo) {
        this.creditsTo = creditsTo;
    }
    
    public Double getGpaFrom() {
        return gpaFrom;
    }
    
    public void setGpaFrom(Double gpaFrom) {
        this.gpaFrom = gpaFrom;
    }
    
    public Double getGpaTo() {
        return gpaTo;
    }
    
    public void setGpaTo(Double gpaTo) {
        this.gpaTo = gpaTo;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    // Build query parameters for GET request
    public String buildQueryParams() {
        StringBuilder query = new StringBuilder();
        boolean hasParam = false;
        
        if (ids != null && !ids.isEmpty()) {
            query.append("ids=").append(join(ids, ","));
            hasParam = true;
        }
        
        if (programs != null && !programs.isEmpty()) {
            if (hasParam) query.append("&");
            query.append("programs=").append(join(programs, ","));
            hasParam = true;
        }
        
        if (creditsFrom != null) {
            if (hasParam) query.append("&");
            query.append("creditsFrom=").append(creditsFrom);
            hasParam = true;
        }
        
        if (creditsTo != null) {
            if (hasParam) query.append("&");
            query.append("creditsTo=").append(creditsTo);
            hasParam = true;
        }
        
        if (gpaFrom != null) {
            if (hasParam) query.append("&");
            query.append("gpaFrom=").append(gpaFrom);
            hasParam = true;
        }
        
        if (gpaTo != null) {
            if (hasParam) query.append("&");
            query.append("gpaTo=").append(gpaTo);
            hasParam = true;
        }
        
        if (sortBy != null && !sortBy.isEmpty()) {
            if (hasParam) query.append("&");
            query.append("sortBy=").append(sortBy);
        }
        
        return query.toString();
    }
    
    private <T> String join(List<T> list, String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) result.append(separator);
            result.append(list.get(i).toString());
        }
        return result.toString();
    }
}

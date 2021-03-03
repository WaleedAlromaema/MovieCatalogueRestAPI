package com.alromaema.projects.moviecatalogsystem.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BindValidator {

    public BindValidator() {
        this(null);
    }

    public BindValidator(Integer id) {
        this(null, id);
    }

    public BindValidator(Integer pathId, Integer bodyId) {
        if (pathId == null && bodyId != null) {
            addBodyIdError(bodyId, "shouldnt indicated");
        }
        if ( (pathId != null && bodyId != null) && !pathId.equals(bodyId)) {
            addBodyIdError(bodyId, String.format("have not match pathId: %d", pathId));
        }
    }

    private void addBodyIdError(Integer bodyId, String message) {
        ErrorMapping error = new ErrorMapping();
        error.setObjectName("body");
        error.setFieldName("id");
        error.setFieldValue(bodyId.toString());
        error.setErrorMessage(message);
        addError(error);
    }

	private final List<ErrorMapping> errorMappings = new ArrayList<ErrorMapping>();

	public void addError(ErrorMapping errorMapping) {
		this.errorMappings.add(errorMapping);
	}

	public void addAllErrors(BindingResult bindingResult) {
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			ErrorMapping error = new ErrorMapping();
			error.setObjectName(fieldError.getObjectName());
			error.setFieldName(fieldError.getField());
			error.setFieldValue(String.valueOf(fieldError.getRejectedValue()));
			error.setErrorMessage(fieldError.getDefaultMessage());
			addError(error);
		}
	}

	public String toJSON() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String json_errors = "";
		try {
			json_errors = mapper.writeValueAsString(errorMappings);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json_errors;
	}

	@Override
	public String toString() {
		return "BindValidator [errorMappings=" + errorMappings + "]";
	}

	protected static class ErrorMapping {

		private String objectName;
		private String fieldName;
		private String fieldValue;
		private String errorMessage;

		public ErrorMapping() {
			this.objectName = "";
			this.fieldName = "";
			this.fieldValue = "";
			this.errorMessage = "";
		}

		protected void setObjectName(String objectName) {
			this.objectName = objectName;
		}

		protected void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		protected void setFieldValue(String fieldValue) {
			this.fieldValue = fieldValue;
		}

		protected void setErrorMessage(String error_message) {
			this.errorMessage = error_message;
		}

		@Override
		public String toString() {
			return "ErrorMapping [objectName=" + objectName + ", fieldName=" + fieldName + ", fieldValue=" + fieldValue
					+ ", errorMessage=" + errorMessage + "]";
		}

	}

}

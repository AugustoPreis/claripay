package com.augustopreis.claripay.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SwaggerResponses.class)
@ApiResponse
public @interface SwaggerResponse {

  @AliasFor(annotation = ApiResponse.class, attribute = "responseCode")
  String responseCode() default "200";

  @AliasFor(annotation = ApiResponse.class, attribute = "description")
  String description() default "";

  @AliasFor(annotation = ApiResponse.class, attribute = "content")
  Content[] content() default {};

  @AliasFor(annotation = ApiResponse.class, attribute = "headers")
  Header[] headers() default {};

  @AliasFor(annotation = ApiResponse.class, attribute = "links")
  Link[] links() default {};
}

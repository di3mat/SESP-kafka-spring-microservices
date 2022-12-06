package it.di3mat.sesplib.logger;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Slf4j
@Component
public class ApiLoggerFilter extends AbstractRequestLoggingFilter {

  final String LOG_TAG = this.getClass().getSimpleName();

  @Override
  protected void beforeRequest(HttpServletRequest request, String message) {
    this.apiLogging(request, null);
  }

  @Override
  protected void afterRequest(HttpServletRequest request, String message) {
  }

  private void apiLogging(HttpServletRequest req, @Nullable String payload) {
    log.info(
        "[{}] {} {}:{}",
        LOG_TAG,
        req.getRemoteUser() != null ? req.getRemoteUser() : "-",
        req.getMethod(),
        req.getRequestURI());
  }
}

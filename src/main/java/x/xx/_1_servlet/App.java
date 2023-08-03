package x.xx._1_servlet;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class App {

    public static void main(String[] args) {

        final int port = 9999;

        // Factory 클래스는 Tomcat WebServer를 별 다른 수동 설정 없이 쉽게 인스턴스를 만들어주는,
        // 이름 그대로 공장처럼 자동으로 찍어주는 클래스. host, port 등을 수동으로 설정할 수 있으며,
        // 기본 값은 localhost:8080이다.
        // 이 설정은 Spring을 사용해도 내부적으로 TomcatWebServer를 사용하기 때문에 그대로 적용된다.
        TomcatServletWebServerFactory tomcatFactory = new TomcatServletWebServerFactory(port);

        // Tomcat 이외에 Jetty, Undertow 등도 Servlet 표준에 맞춰 구현한 라이브러리이기 때문에,
        // 설치만 해준다면 그대로 Tomcat 코드 변경 없이 갈아 끼울 수 있다.
        // JettyServletWebServerFactory jettyFactory = new JettyServletWebServerFactory(port);
        // UndertowServletWebServerFactory undertowFactory = new UndertowServletWebServerFactory(port);

        // getWebServer 메소드 내부에서 Tomcat 인스턴스를 생성한 뒤,
        // 콜백으로 주어진 Servlet을 ServletContext에 초기화 및 등록해준다.
        // 그리고 완성된 WebServer를 반환한다.
        WebServer tomcatWebServer = tomcatFactory.getWebServer(
                servletContext -> {
                    // HttpServlet을 상속한 클래스를 Servlet Container인 Tomcat에 등록하고,
                    // 특정 URL 경로에 맵핑할 수 있다.
                    servletContext.addServlet("MainServlet", new MainController())
                            .addMapping("/main");
                    // HttpServlet을 상속한 익명 클래스를 곧바로 만들어서 등록할 수도 있다.
                    servletContext.addServlet("NotFoundServlet", new HttpServlet() {
                                @Override
                                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                                    resp.getWriter().write("Not Found");
                                }
                            })
                            // url을 "/*"으로 맵핑한다는 것은 어떤 경로로 요청이 들어오더라도 매칭된다는 것을 뜻한다.
                            // 즉, Wildcard를 의미한다.
                            .addMapping("/*");
                }
        );

        // 위에서 반환받은 Tomcat WebServer를 실행한다.
        // 이제 Client <-> Server 요청 및 반환을 주고 받을 수 있다.
        tomcatWebServer.start();
    }
}

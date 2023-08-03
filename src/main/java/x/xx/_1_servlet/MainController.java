package x.xx._1_servlet;

import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {
    /*
     * HttpServlet의 service 메소드는 모든 HTTP METHOD를 수용하지만,
     * doGet, doPost 등
     * 각 HTTP METHOD와 매칭되는 메소드도 존재한다.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     /*
      * 만약 service 메소드를 override하고 GET 요청만 받으려면 아래와 같이 추가적인 로직이 필요했을 것이다.
      * String method = req.getMethod();
      *
      * if (!method.equals("GET")) {
      *   resp.setHeader()
      *   return;
      * }
     */
        String method = req.getMethod();
        System.out.printf("HTTP METHOD: %s", method);

        /*
         * Get Query Parameter from URL
         */
        String a = req.getParameter("a");
        String b = req.getParameter("b");

        /*
         * Set Response Header
         */

        // Response Status를 200 OK로 설정한다.
        resp.setStatus(HttpStatus.OK.value());

        // HTTP Spec에 존재하지 않는 CUSTOM HEADER 추가
        resp.setHeader("custom-header", "main");


        /*
         * Set Response Body
         */

        // 응답 버퍼에 String을 write한다.
        resp.getWriter().write("GET /main HTTP/1.1\n");
        // Network 전송도 결국 입출력이며, write, print, printf 등
        // 사용하는 함수명도 파일 입출력과 완전히 동일한 것을 확인할 수 있다.
        resp.getWriter().printf("[Query Parameters] a: %s, b: %s\n", a, b);

        // 응답 버퍼에 저장된 문자열을 클라이언트로 전송한다.
        // 응답 버퍼에 작성한 내용들은 Response Body에 들어간다.
        resp.getWriter().flush();
    }

    /*
     * POST /main 으로 요청이 들어올 경우, doPost가 호출된다.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // req.getParameter는 GET인 경우 URL 뒤의 Query Parameter를 파싱한다.
        // GET은 일반적으로 Request Body를 포함하지 않기 때문이다.
        //
        // GET 외 POST, PUT, DELETE 등의 경우
        // URL 뒤에 Query Parameter를 붙이지 않고
        // Request Body에 데이터를 넣어 전송한다.
        //
        // 그래서 똑같이 req.getParameter를 호출해도 GET은 URL에서,
        // POST, PUT, DELETE는 Request Body에서 파싱한 데이터를 가져온다.

        /*
         * Get Query Parameter from Request Body
         */
        String a = req.getParameter("a");
        String b = req.getParameter("b");

        /*
         * Set Response Header
         */
        // Response Status를 201 CREATED로 설정한다.
        resp.setStatus(HttpStatus.CREATED.value());

        /*
         * Set Response Body
         */
        resp.getWriter().write("POST /main HTTP/1.1");
        // flush를 호출하지 않아도 자동으로 resp.getWriter로 받아온
        // Response Writer 버퍼에 저장된 데이터를 클라이언트로 flush하여 전송
    }
}

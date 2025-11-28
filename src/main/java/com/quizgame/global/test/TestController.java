package com.quizgame.global.test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.quizgame.global.kafka.TestKafkaProducer;
import com.quizgame.global.session.SessionUser;
import com.quizgame.global.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestKafkaProducer producer;

    @GetMapping("/test/kafka")
    public String testKafka() {
        log.info("=== /test/kafka ===");
        producer.send("hello kafka from quizgame");
        return "ok";
    }

    @GetMapping("/chk/session")
    public SessionUser chkSession() {
        log.info("=== /chk/session ===");
        SessionUser sessionUser = SessionUtil.getSessionUser();
        System.out.println("sessionUser = " + sessionUser);
        return sessionUser;
    }

    @GetMapping("/test/ai")
    public Response testAi() {
        log.info("=== /test/ai ===");
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .baseUrl("https://openrouter.ai/api/v1")
                .build();

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model("x-ai/grok-4.1-fast:free")
                .input("""
                        너는 퀴즈 출제 AI다.

                        요구사항:
                        1. 퀴즈는 총 3개를 생성한다.
                        2. 퀴즈 주제는 모두 "게임" 관련된 내용이어야 한다.
                        3. 아래 JSON 스키마에 맞게만 출력해야 한다.

                        JSON 스키마 (배열 형태):
                        [
                          {
                            "content": "문제 내용 (문장)",
                            "questionType": 1 또는 2,  // 1 = 주관식, 2 = 객관식
                            "difficulty": 1~5 정수,   // 1이 가장 쉬움
                            "answers": [
                              {
                                "answer": "보기 또는 정답 문자열",
                                "correctYn": true 또는 false
                              }
                            ]
                          },
                          ...
                        ]

                        추가 규칙:
                        - 객관식(questionType = 2)인 경우:
                          - answers 배열에 4개의 보기를 넣어라.
                          - 그 중 하나만 correctYn = true, 나머지는 false.
                        - 주관식(questionType = 1)인 경우:
                          - answers 배열에 정답이 될 수 있는 다양한 표현을 여러 개 넣어라.
                            예시: "세종", "세종대왕"
                          - 모든 정답 표현에 correctYn = true 로 설정한다.

                        출력 형식 규칙 (매우 중요):
                        - 반드시 JSON 배열만 출력한다.
                        - JSON 외의 설명 텍스트를 절대 포함하지 마라.
                        - ```json 같은 마크다운 코드는 사용하지 마라.

                        이제 위의 형식에 맞는 퀴즈를 생성해라.
                        """)
                .build();

        Response response = client.responses().create(params);
        String text = response.output().get(1).asMessage().content().get(0).outputText().orElseThrow().text();
        System.out.println("responseOutputText = " + text);
        return response;
    }
}

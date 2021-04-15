package com.quartciphers.skillscy;

import com.quartciphers.skillscy.dto.InputName;
import com.quartciphers.skillscy.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class QCMainWSController {

    @PostMapping("/info/{second}")
    public ResponseEntity<ResponseMessage> returnsHelloWorld(@RequestHeader("first") String firstPerson,
                                                             @PathVariable("second") String secondPerson,
                                                             @RequestParam(value = "third", required = false, defaultValue = "") String thirdPerson,
                                                             @RequestBody InputName inputName) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Hello world to "+ firstPerson +", "+ secondPerson +", "+ thirdPerson +", " + inputName.getForth());
        return ResponseEntity.ok(responseMessage);
    }

}

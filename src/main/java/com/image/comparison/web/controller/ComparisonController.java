package com.image.comparison.web.controller;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.image.comparison.service.ImageManager;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author Pogorelov on 9/15/2016
 */
@RestController
public class ComparisonController {

    @Autowired
    private ImageManager imageManager;

    @RequestMapping(value = "/rest/images/compare", consumes = { "multipart/mixed", "multipart/form-data" },  method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<byte[]> compareImages(MultipartFile image1, MultipartFile image2) throws Exception {

        BufferedImage bufferedImage = imageManager.uploadAndProcess(image1, image2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        InputStream in = new ByteArrayInputStream(baos.toByteArray());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        byte[] bytes = Base64.encodeBase64(IOUtils.toByteArray(in));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }
}
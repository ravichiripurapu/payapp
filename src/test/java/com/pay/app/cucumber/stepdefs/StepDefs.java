package com.pay.app.cucumber.stepdefs;

import com.pay.app.PayappApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = PayappApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}

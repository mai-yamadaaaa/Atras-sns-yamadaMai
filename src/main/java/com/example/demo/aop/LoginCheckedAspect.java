package com.example.demo.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.model.MyAccount;

@Aspect
@Component
public class LoginCheckedAspect {

	@Autowired
	MyAccount myAccount;

	@Before("execution(* com.example.demo.controller..*.*(..))")
	public void loginCheck(JoinPoint joinPoint) {
		// ログインしてなければリダイレクトや例外など
		if (myAccount == null || myAccount.getName() == null || myAccount.getName().length() == 0) {
			System.out.println("ようこそ、ゲストさん。ログインしてください");
		} else {
			System.out.println(myAccount.getName() + "さんがログインしました");
		}

	}

	@Around("execution(* com.example.demo.controller..*.*(..))")
	public Object loginChecked(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		String path = request.getRequestURI();

		// ログインページや登録ページは対象外
		if (path.contains("/login") ||
				path.contains("/logout") ||
				path.contains("/user/create")) {
			return joinPoint.proceed();
		}

		// セッションスコープの myAccount を使ってログイン判定
		if (myAccount == null || myAccount.getName() == null || myAccount.getName().isEmpty()) {
			session.setAttribute("redirectAfterLogin", path);

			// 呼び出し元メソッドの戻り値が String であるかチェック（型チェック）
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			if (signature.getReturnType() == String.class) {
				return "redirect:/login";
			}

			// 他の型に対応したいならここで分岐
			throw new RuntimeException("ログインが必要です");
		}

		System.out.println(myAccount.getName() + " さんがアクセス中 → " + path);
		return joinPoint.proceed();
	}
}
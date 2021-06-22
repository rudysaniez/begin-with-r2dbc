<p align="center">
	<a href="" rel="noopener">
	 <img width=190px height=160px src="store.jpg" alt="Project logo">
 </a>
</p>

<h3 align="center">Pro Replenishment WebFlux R2dbc H2 API</h3>

<div align="center">

  [![Status](https://img.shields.io/badge/status-active-success.svg)]()

</div>

---

This readme file explains the interest of this API. You will found how to make calls and you will have information on the document returned.  
Good reading! 🌈

## 📝 Table of Contents
- [Presentation](#presentation)
- [What is R2dbc?](#what-is-r2dbc)
---

## Presentation

Reactive APIs are very powerful to handle and serve large amounts of data and large number of requests in a web application.

The stream reactive model is equivalent to the `Publisher-Subscriber` pattern. So, the client subscribes to `events` on the server which pushes the data through several operators which contains a data stream.

It can't be useful in CRUD application. However, it offers improved performance versus the standard `request-response` paradigm. In addition, the implementation is very easy with Spring-Boot which integration the feature named `spring-boot-starter-webflux`. Spring WebFlux is very powerful and uses the Reactor Project.

A other thing, in the SQL World, without R2dbc, the Reactive programming paradigm is useless. Indeed, the JDBC driver is blocking by default. Now, with R2dbc, it possible to use H2/Postgres/MySQL in the Reactive World.

## What is R2dbc?

<p align="center">
	<a href="" rel="noopener">
	 <img width=600px height=180px src="r2dbc.png" alt="Project logo">
 </a>
</p>
import React, { useState, useEffect } from "react";
import {
  signInWithPopup,
  GoogleAuthProvider,
  signInWithEmailAndPassword,
} from "firebase/auth";
import { auth } from "../firebase";
import Head from "next/head";
import Link from "next/link";
import { useAuth } from "../security/AuthContext";
import { useRouter } from "next/router";
import { FaGoogle } from "react-icons/fa";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();
  const authContext = useAuth();

  useEffect(() => {
    if (authContext.isAuthenticated) {
      router.push("/");
    }
  }, [authContext.isAuthenticated, router]);

  const emailSignIn = (event) => {
    setError(null);
    signInWithEmailAndPassword(auth, email, password)
      .then((result) => {
        const username = result.user.email;
        const idToken = result.user.accessToken;

        localStorage.setItem("token", idToken);

        authContext.login(username, idToken);
      })
      .catch((error) => {
        alert("Unable to sign in with email and password");
      });
    event.preventDefault();
  };

  function googleSignIn() {
    const provider = new GoogleAuthProvider();

    signInWithPopup(auth, provider)
      .then((result) => {
        const username = result.user.email;
        const idToken = result._tokenResponse.idToken;

        localStorage.setItem("token", idToken);

        authContext.login(username, idToken);
      })
      .catch((error) => {
        alert("Unable to sign in with Google");
      });
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-b from-[#2e026d] to-[#15162c]">
      <Head>
        <title>Login</title>
      </Head>
      <h1 className="text-3xl font-bold text-white mb-8">
        Login to Culinary Companion
      </h1>
      <form onSubmit={emailSignIn} className="w-full max-w-sm">
        <div className="mb-4">
          <label htmlFor="loginEmail" className="block text-white mb-2">
            Email
          </label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            id="loginEmail"
            className="w-full text-black px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-indigo-500"
            placeholder="Email"
          />
        </div>
        <div className="mb-6">
          <label htmlFor="loginPassword" className="block text-white mb-2">
            Password
          </label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            id="loginPassword"
            className="w-full text-black px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-indigo-500"
            placeholder="Password"
          />
        </div>
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full mb-3"
        >
          Login
        </button>
      </form>
      <button
        className="mt-4 bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-1/3"
        onClick={googleSignIn}
      >
        <div className="flex items-center justify-center">
          Login With <FaGoogle className="ml-2" />
        </div>
      </button>
      <div className="text-white mt-5">
        <p>
          Don&apos;t have an account?{" "}
          <Link href="/signup" className="text-blue-500 hover:text-blue-700">
            Sign Up
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;

import { useState, useEffect } from "react";
import { useRouter } from "next/router";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { useAuth } from "../security/AuthContext";
import { auth } from "../firebase";
import Link from "next/link";
import Head from "next/head";

const Signup = () => {
  const [email, setEmail] = useState("");
  const [passwordOne, setPasswordOne] = useState("");
  const [passwordTwo, setPasswordTwo] = useState("");
  const router = useRouter();
  const authContext = useAuth();

  useEffect(() => {
    if (authContext.isAuthenticated) {
      router.push("/");
    }
  }, [authContext.isAuthenticated, router]);

  const onSubmit = (event) => {
    if (!email || !passwordOne || !passwordTwo) {
      alert("Please fill in all fields");
      return;
    }

    if (passwordOne === passwordTwo)
      createUserWithEmailAndPassword(auth, email, passwordOne)
        .then((result) => {
          router.push("/login");
        })
        .catch((error) => {
          alert("Unable to sign up");
        });
    else alert("Passwords do not match");
    event.preventDefault();
  };

  if (authContext.isAuthenticated) {
    return <h1>Loading...</h1>;
  } else {
    return (
      <div className="signupPage min-h-screen flex flex-col justify-center items-center bg-gradient-to-b from-[#2e026d] to-[#15162c] text-white">
        <Head>
          <title>Sign Up</title>
        </Head>
        <h1 className="text-3xl font-bold mb-8">
          Sign Up for Culinary Companion
        </h1>
        <form className="signupForm w-full max-w-md" onSubmit={onSubmit}>
          <div className="mb-4">
            <label htmlFor="email" className="block mb-2">
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              name="email"
              id="email"
              className="w-full text-black px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-indigo-500"
              placeholder="Email"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="passwordOne" className="block mb-2">
              Password
            </label>
            <input
              type="password"
              name="passwordOne"
              value={passwordOne}
              onChange={(event) => setPasswordOne(event.target.value)}
              id="passwordOne"
              className="w-full text-black px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-indigo-500"
              placeholder="Password"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="passwordTwo" className="block mb-2">
              Confirm Password
            </label>
            <input
              type="password"
              name="passwordTwo"
              value={passwordTwo}
              onChange={(event) => setPasswordTwo(event.target.value)}
              id="passwordTwo"
              className="w-full text-black px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-indigo-500"
              placeholder="Confirm Password"
            />
          </div>
          <button
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 focus:outline-none focus:bg-blue-600"
            type="submit"
          >
            Sign Up
          </button>
        </form>
        <div className="loginLink mt-4">
          <p>
            Already have an account?{" "}
            <Link href="/login" className="text-blue-500 hover:underline">
              Log In
            </Link>
          </p>
        </div>
      </div>
    );
  }
};

export default Signup;

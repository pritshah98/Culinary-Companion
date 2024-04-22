import React from "react";
import withAuth from "../security/withAuth";
import { useAuth } from "../security/AuthContext";
import { useRouter } from "next/router";
import Head from "next/head";
import TransitionEffect from "@/components/TransitionEffect";
import Layout from "@/components/Layout";

const Home = () => {
  const authContext = useAuth();
  const username = authContext.username;
  const router = useRouter();

  function logout() {
    authContext.logout();
  }

  function displayAllRecipes() {
    router.push("/recipes/all");
  }

  function viewUserRecipes() {
    router.push("/recipes/myrecipes");
  }

  function viewUserIngredients() {
    router.push("/myingredients");
  }

  return (
    <>
      <Head>
        <title>Culinary Companion</title>
      </Head>
      <TransitionEffect />
      <main className="flex min-h-screen flex-col items-center justify-center bg-gradient-to-b from-[#2e026d] to-[#15162c]">
        <Layout>
          <h1 className="text-white text-4xl font-bold mb-8">
            Welcome to Culinary Companion {username}
          </h1>
          <div className="flex items-center space-x-4">
            <button
              className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700"
              onClick={displayAllRecipes}
            >
              Display All Recipes
            </button>
            <button
              className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700"
              onClick={viewUserRecipes}
            >
              View My Recipes
            </button>
            <button
              className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700"
              onClick={viewUserIngredients}
            >
              View My Ingredients
            </button>
            <button
              className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700"
              onClick={logout}
            >
              Logout
            </button>
          </div>
        </Layout>
      </main>
    </>
  );
};

export default withAuth(Home);

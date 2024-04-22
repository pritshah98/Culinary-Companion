import { AuthProvider } from "@/security/AuthContext";
import { AnimatePresence } from "framer-motion";
import "@/styles/globals.css";

export default function App({ Component, pageProps }) {
  return (
    <AuthProvider>
      <AnimatePresence mode="wait">
        <Component {...pageProps} />
      </AnimatePresence>
    </AuthProvider>
  );
}

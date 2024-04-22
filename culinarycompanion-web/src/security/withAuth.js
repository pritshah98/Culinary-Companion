import { useRouter } from 'next/router';
import { useEffect } from 'react';
import { useAuth } from './AuthContext';

/**
 * Creates a higher-order component that checks authentication status before rendering the wrapped component.
 *
 * @param {function} WrappedComponent - The component to be rendered if the user is authenticated.
 * @return {function} The component that handles authentication logic.
 */
const withAuth = (WrappedComponent) => {
  const AuthComponent = (props) => {
    const router = useRouter();

    const authContext = useAuth();

    useEffect(() => {

      if (!authContext.isAuthenticated && !localStorage.getItem("isAuth")) {
        router.push('/login'); // Redirect unauthenticated users to the login page
      }
    }, [router, authContext.isAuthenticated]);

    return authContext.isAuthenticated ? <WrappedComponent {...props} /> : null;
  };

  return AuthComponent;
};

export default withAuth;
export default function authHeader() {
  const user = JSON.parse(localStorage.getItem('user'));

  if (user && user.token) {
    console.log(`${user.tokenType} ${user.token}`);
    return { Authorization: `${user.tokenType} ${user.token}` }; // for Spring Boot back-end
  } else {
    return {};
  }
}

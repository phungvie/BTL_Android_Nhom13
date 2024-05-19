import { useLocation } from 'react-router-dom';
import * as loginServices from "../../apiServices/loginservices";
import './home.css';

function Home() {


  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const code = queryParams.get('code');
  if (code != null ) {
    loginServices
      .login_github(code)
      .then((viet) => {
        console.log(viet);
      })
      // window.location.href = 'http://localhost:3000'
  }
  return (
    <main className='homeRight'>
      <h1>viet</h1>
    </main>);
}




export default Home;
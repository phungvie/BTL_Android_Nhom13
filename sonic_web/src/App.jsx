// import 'bootstrap/dist/css/bootstrap.min.css';
import { Route, Routes } from 'react-router-dom';
import './App.css';
import Footer from './components/Footer';
import HeaderHome from './components/HeaderHome';
import HeaderLogin from './components/HeaderLogin';
import Home from './components/Home';
import Login from './components/Login';
function App() {
  return (
    <div className="App">

      <Routes>
        <Route path='/' element={ <HeaderHome />} />
        <Route path='/login' element={ <HeaderLogin />} />
      </Routes>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<Login />} />
      </Routes>
      <Footer />

    </div>
  );
}

export default App;

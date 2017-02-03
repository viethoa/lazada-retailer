import React from 'react';
import LoginCSS from './login.css';
import Request from 'superagent';

class Login extends React.Component {

  btnLoginClicked() {
    var url = "http://localhost:8080"
  }

  render() {
    return (
      <div className="login-page">
        <div className="form">
          <form className="login-form">
            <input type="text" placeholder="username"/>
            <input type="password" placeholder="password"/>
            <button onclick="btnLoginClicked()">login</button>
            <p className="message">Bạn không có tài khoản?</p>
            <p className="highlight">Hãy gọi cho chúng tôi 01286.84.83.84</p>
          </form>
        </div>
      </div>
    );
  }
}

export default Login;
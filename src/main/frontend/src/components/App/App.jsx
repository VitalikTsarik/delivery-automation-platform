import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import Header from "../Header/Header";
import Login from "../auth/login";
import Register from "../auth/register";
import Home from "../Home/Home";
import Profile from "../Profile/Profile";
import BoardUser from "../board-user.component";

class App extends Component {
  render() {
    return (
      <Router>
        <Header />
          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/home"]} component={Home} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
              <Route exact path="/profile" component={Profile} />
              <Route path="/user" component={BoardUser} />
            </Switch>
          </div>
      </Router>
    );
  }
}

export default App;

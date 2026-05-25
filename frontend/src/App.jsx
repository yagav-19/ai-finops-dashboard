import { useState, useEffect } from "react";
import axios from "axios";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid
} from "recharts";

function App() {

  const [loggedIn, setLoggedIn] =
    useState(
      localStorage.getItem("loggedIn")
      === "true"
    );

  const [username, setUsername] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [awsCost, setAwsCost] =
    useState("");

  const [alertMessage, setAlertMessage] =
    useState("");

  const [trendData, setTrendData] =
    useState([]);

  const [question, setQuestion] =
    useState("");

  const [answer, setAnswer] =
    useState("");

  useEffect(() => {

    if (loggedIn) {

      fetchAwsCost();
      fetchAlert();
      fetchTrend();
    }

  }, [loggedIn]);

  const signup = async () => {

    const response =
      await axios.post(
        "http://localhost:8080/auth/signup",
        {
          username,
          password
        }
      );

    alert(response.data);
  };

  const login = async () => {

    const response =
      await axios.post(
        "http://localhost:8080/auth/login",
        {
          username,
          password
        }
      );

    if (
      response.data ===
      "Login successful"
    ) {

      localStorage.setItem(
        "loggedIn",
        "true"
      );

      setLoggedIn(true);

    } else {

      alert(response.data);
    }
  };

  const logout = () => {

    localStorage.removeItem(
      "loggedIn"
    );

    setLoggedIn(false);
  };

  const fetchAwsCost =
    async () => {

      const response =
        await axios.get(
          "http://localhost:8080/api/cloud-costs/aws-cost"
        );

      setAwsCost(response.data);
    };

  const fetchAlert =
    async () => {

      const response =
        await axios.get(
          "http://localhost:8080/api/cloud-costs/alert"
        );

      setAlertMessage(
        response.data
      );
    };

  const fetchTrend =
    async () => {

      const response =
        await axios.get(
          "http://localhost:8080/api/cloud-costs/trend"
        );

      const months =
        [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun"
        ];

      const data =
        response.data.map(
          (cost, index) => ({
            month:
              months[index],
            cost
          })
        );

      setTrendData(data);
    };

  const askAi = async () => {

    try {

      const response =
        await axios.get(
          "http://localhost:8080/api/cloud-costs/chat",
          {
            params: {
              question
            }
          }
        );

      setAnswer(
        response.data
      );

    } catch {

      setAnswer(
        "AI chatbot error"
      );
    }
  };

  if (!loggedIn) {

    return (

      <div className="min-h-screen bg-slate-950 text-white flex flex-col items-center justify-center">

        <h1 className="text-5xl font-bold mb-10">
          GenAI FinOps Login 🔐
        </h1>

        <input
          className="p-4 rounded-xl text-black mb-4 w-80"
          placeholder="Username"
          value={username}
          onChange={(e) =>
            setUsername(
              e.target.value
            )
          }
        />

        <input
          className="p-4 rounded-xl text-black mb-4 w-80"
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) =>
            setPassword(
              e.target.value
            )
          }
        />

        <div className="flex gap-4">

          <button
            onClick={signup}
            className="bg-green-500 px-6 py-3 rounded-xl"
          >
            Signup
          </button>

          <button
            onClick={login}
            className="bg-cyan-500 px-6 py-3 rounded-xl"
          >
            Login
          </button>

        </div>

      </div>
    );
  }

  return (

    <div className="min-h-screen bg-slate-950 text-white p-10">

      <div className="flex justify-between items-center mb-10">

        <h1 className="text-5xl font-bold">
          GenAI FinOps Dashboard 🚀
        </h1>

        <button
          onClick={logout}
          className="bg-red-500 px-5 py-2 rounded-xl"
        >
          Logout
        </button>

      </div>

      <div className="bg-slate-800 p-8 rounded-3xl mb-10">

        <h2 className="text-3xl font-bold">
          AWS Monthly Cost ☁️
        </h2>

        <p className="text-5xl mt-4 text-yellow-400">
          $ {Number(awsCost).toFixed(2)}
        </p>

        <div className="mt-5 flex gap-4">

          <button
            onClick={fetchAwsCost}
            className="bg-blue-500 px-5 py-3 rounded-xl"
          >
            Refresh Cost
          </button>

          <button
            onClick={() =>
              window.open(
                "http://localhost:8080/api/cloud-costs/report"
              )
            }
            className="bg-purple-500 px-5 py-3 rounded-xl"
          >
            Download PDF Report
          </button>

        </div>

      </div>

      <div className="bg-slate-800 p-8 rounded-3xl mb-10">

        <h2 className="text-3xl font-bold mb-4">
          Cost Alert 🚨
        </h2>

        <p className="text-2xl text-orange-400">

          {alertMessage}

        </p>

      </div>

      <div className="bg-slate-800 p-8 rounded-3xl mb-10">

        <h2 className="text-3xl font-bold mb-6">
          AWS Cost Trend 📈
        </h2>

        <div className="w-full overflow-x-auto">

          <LineChart
            width={900}
            height={320}
            data={trendData}
          >

            <CartesianGrid strokeDasharray="3 3" />

            <XAxis dataKey="month" />

            <YAxis />

            <Tooltip />

            <Line
              type="monotone"
              dataKey="cost"
              stroke="#38bdf8"
              strokeWidth={3}
            />

          </LineChart>

        </div>

      </div>

      <div className="bg-slate-800 p-8 rounded-3xl">

        <h2 className="text-3xl font-bold mb-6">
          Ask AI About Cloud Costs 🤖
        </h2>

        <input
          value={question}
          onChange={(e) =>
            setQuestion(
              e.target.value
            )
          }
          placeholder="How can I reduce AWS cost?"
          className="w-full p-4 rounded-xl text-black"
        />

        <button
          onClick={askAi}
          className="mt-4 bg-cyan-500 px-6 py-3 rounded-xl font-bold"
        >
          Ask AI
        </button>

        <div className="mt-8 whitespace-pre-line text-slate-300">

          {answer}

        </div>

      </div>

    </div>
  );
}

export default App;
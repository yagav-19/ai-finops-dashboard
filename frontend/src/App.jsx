import { useEffect, useState } from "react";
import axios from "axios";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer
} from "recharts";

function App() {

  const [costs, setCosts] = useState([]);
  const [prediction, setPrediction] = useState(0);
  const [recommendation, setRecommendation] = useState("");

  const chartData = [
    { month: "Jan", cost: 18000 },
    { month: "Feb", cost: 22000 },
    { month: "Mar", cost: 25000 },
    { month: "Apr", cost: 27000 },
    { month: "May", cost: 32000 }
  ];

  useEffect(() => {

    fetchCloudCosts();
    fetchPrediction();
    fetchRecommendation();

  }, []);

  const fetchCloudCosts = async () => {

    try {

      const response = await axios.get(
        "http://localhost:8080/api/cloud-costs"
      );

      setCosts(response.data);

    } catch (error) {

      console.error("Error fetching cloud costs:", error);
    }
  };

  const fetchPrediction = async () => {

    try {

      const response = await axios.get(
        "http://localhost:8080/api/cloud-costs/predict?currentCost=25000&growth=15"
      );

      setPrediction(response.data);

    } catch (error) {

      console.error("Error fetching prediction:", error);
    }
  };

  const fetchRecommendation = async () => {

    try {

      const response = await axios.get(
        "http://localhost:8080/api/cloud-costs/ai-recommend?monthlyCost=50000&savings=12000"
      );

      setRecommendation(response.data);

    } catch (error) {

      console.error("Error fetching AI recommendation:", error);
    }
  };

  return (

    <div className="min-h-screen bg-slate-950 text-white p-10">

      <h1 className="text-6xl font-bold text-center mb-12">
        AI FinOps Cloud Dashboard 🚀
      </h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

        <div className="bg-slate-800 p-8 rounded-3xl shadow-2xl hover:scale-105 transition duration-300">

          <h2 className="text-3xl font-bold">
            Total Records
          </h2>

          <p className="text-6xl mt-4 text-cyan-400">
            {costs.length}
          </p>

        </div>

        <div className="bg-slate-800 p-8 rounded-3xl shadow-2xl hover:scale-105 transition duration-300">

          <h2 className="text-3xl font-bold">
            Predicted Cost
          </h2>

          <p className="text-5xl mt-4 text-green-400">
            ₹ {prediction}
          </p>

        </div>

      </div>

      <div className="bg-slate-800 p-8 rounded-3xl shadow-2xl mt-10">

        <h2 className="text-4xl font-bold mb-6">
          AI Recommendation 🤖
        </h2>

        <p className="text-xl leading-relaxed text-slate-300 whitespace-pre-line">
          {recommendation}
        </p>

      </div>

      <div className="bg-slate-800 p-8 rounded-3xl shadow-2xl mt-10">

        <h2 className="text-4xl font-bold mb-8">
          Cloud Cost Trends 📈
        </h2>

        <ResponsiveContainer width="100%" height={350}>

          <LineChart data={chartData}>

            <CartesianGrid stroke="#444" />

            <XAxis dataKey="month" />

            <YAxis />

            <Tooltip />

            <Line
              type="monotone"
              dataKey="cost"
              stroke="#06b6d4"
              strokeWidth={4}
            />

          </LineChart>

        </ResponsiveContainer>

      </div>

      <div className="bg-slate-800 p-8 rounded-3xl shadow-2xl mt-10">

        <h2 className="text-4xl font-bold mb-8">
          Saved Cloud Costs 💾
        </h2>

        {
          costs.map((cost) => (

            <div
              key={cost.id}
              className="border-b border-slate-600 py-6"
            >

              <p className="text-xl">
                <strong>Monthly Cost:</strong>
                ₹ {cost.monthlyCost}
              </p>

              <p className="text-xl mt-2">
                <strong>Predicted Cost:</strong>
                ₹ {cost.predictedCost}
              </p>

              <p className="text-xl mt-2">
                <strong>Savings Opportunity:</strong>
                ₹ {cost.savingsOpportunity}
              </p>

            </div>

          ))
        }

      </div>

    </div>
  );
}

export default App;
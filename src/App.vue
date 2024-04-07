<template>
  <div id="app">
    <div class="header">
      <h1>Car List</h1>
      <div class="filter-container">
        <label for="filter">Filter:</label>
        <select v-model="selectedFilter" @change="applyFilter" id="filter">
          <option value="">Select</option>
          <option value="All">All</option>
          <option value="2">2 Wheels</option>
          <option value="4">4 Wheels</option>
        </select>
      </div>
    </div>
    <InputCar @add="addCar" @check-category="checkCategory"/>
    <div v-if="message">{{ message }}</div>
    <div v-if="cars.length > 0">
      <table class="car-table">
        <thead>
          <tr>
            <th>Car Name</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(car, index) in cars" :key="index">
            <td>{{ car }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import InputCar from "./components/InputCar.vue";
export default {
  name: "App",
  components: {
    InputCar
  },
  data() {
    return {
      message: "",
      cars: [],
      selectedFilter: ""
    };
  },
  methods: {
    addCar(carDetails) {
      fetch("http://localhost:8080/api/cars/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(carDetails)
      })
      .then(response => {
        if (response.ok) {
          this.message = `Thanks. I updated the information.`;
          this.fetchAllCars();
        } else {
          return response.text().then(errorMessage => {
            this.message = `Error: ${errorMessage}`;
          });
        }
      })
      .catch(error => {
        console.error("Error:", error);
        this.message = "An error occurred. System is close, Please try again later.";
      });
    },
    checkCategory(carName) {
      fetch(`http://localhost:8080/api/cars/${carName}`)
        .then(response => {
          if (!response.ok) {
            throw new Error('Car not found');
          }
          return response.json();
        })
        .then(data => {
          if (data.wheels === 2 || data.wheels === 4) {
            this.message = `"${carName}" has ${data.wheels} wheels.`;
          } else {
            this.message = `"${carName}" is in the list, but the number of wheels is unknown.`;
          }
        })
        .catch(error => {
          console.error("Error:", error);
          this.message = `"${carName}" is not in my list.`;
        });
    },
    fetchAllCars() {
      fetch("http://localhost:8080/api/cars/all")
        .then(response => response.json())
        .then(data => {
          this.cars = data;
        })
        .catch(error => {
          console.error("Error:", error);
          this.message = "An error occurred. System is close, Please try again later.";
        });
    },
    applyFilter() {
      if (this.selectedFilter === "All") {
        this.fetchAllCars();
      } else {
        this.filterByWheels(parseInt(this.selectedFilter));
      }
    },
    filterByWheels(wheels) {
      fetch(`http://localhost:8080/api/cars/filter?wheels=${wheels}`)
        .then(response => response.json())
        .then(data => {
          this.cars = data;
        })
        .catch(error => {
          console.error("Error:", error);
          this.message = "An error occurred. System is close, Please try again later.";
        });
    }
  },
  mounted() {
    this.fetchAllCars();
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  width: 500px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.filter-container {
  display: flex;
  align-items: center;
}

.car-table {
  width: 100%;
  border-collapse: collapse;
}

.car-table th,
.car-table td {
  padding: 8px;
  border-bottom: 1px solid #ddd;
  text-align: left;
}

.car-table th {
  background-color: #f2f2f2;
}

.car-table tbody tr:nth-child(even) {
  background-color: #f2f2f2;
}
.filter-container {
  margin-top: 20px; /* Add some space between input rows and filter container */
}

.filter-container label {
  margin-right: 10px; /* Add some space between label and select box */
}

.filter-container select {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  height: 36px;
  width: 200px;
}
</style>

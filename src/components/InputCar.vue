<template>
  <div class="input-car">
    <div class="input-row">
      <label for="carName">Car name:</label>
      <input type="text" id="carName" v-model="carName" class="input-field">
      <button @click="checkCategory" class="btn check-category-btn">Check List</button>
    </div>
    <div class="input-row">
      <label for="wheels">Number of wheels:</label>
      <input type="number" id="wheels" v-model="wheels" class="input-field">
      <button @click="addCar" class="btn add-car-btn">Add Car</button>
    </div>
    <!-- Popup and backdrop -->
    <div v-if="showPopup" class="popup-backdrop">
      <div class="popup">
        <p>System is closing...</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      carName: "",
      wheels: 0,
      showPopup: false 
    };
  },
  methods: {
    addCar() {
      if (!this.showPopup) {
        this.$emit("add", { name: this.carName, wheels: this.wheels });
        this.carName = "";
        this.wheels = 0;
      }
    },
    checkCategory() {
      if (!this.showPopup && this.carName.trim().toLowerCase() === "bye") {
        this.showPopup = true; 
        this.closeSystem();
      } else {
        this.$emit("check-category", this.carName);
      }
    },
    closeSystem() {
      // Send a request to close the system
      fetch("http://localhost:8080/api/cars/shutdown", { method: "POST" })
        .then(response => {
          if (response.ok) {
            console.error("System closed successfully");
          } else {
            console.error("Failed to close system");
          }
        })
        .catch(error => {
          console.error("Error:", error);
          this.showPopup = false;
        });
    }
  }
};
</script>

  
  <style scoped>
  .input-car {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 20px;
  }
  
  .input-row {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }
  
  .input-row label {
    text-align: left; 
    margin-right: 10px; 
    width: 150px;
  }
  
  .input-field {
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 200px;
  }
  
  .btn {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    background-color: #007bff;
    color: #fff;
    transition: background-color 0.3s;
    height: 36px;
  }
  
  .btn:hover {
    background-color: #0056b3;
  }
  .popup-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .popup {
    background-color: white;
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 5px;
    z-index: 9999;
  }

  </style>
  
  
  
<!DOCTYPE html>
<html>
<head>
    <title>File Insurance Claim</title>
    <script>
        // Function to search for patients based on the input in the search box
        function searchPatient() {
            const query = document.getElementById("patientSearch").value;

            // Trigger search only if query length is at least 2 characters
            if (query.length >= 2) {
                const xhr = new XMLHttpRequest();
                // Make an asynchronous GET request to the search endpoint
                xhr.open("GET", "<%=request.getContextPath()%>/searchPatient?query=" + encodeURIComponent(query), true);
                xhr.onreadystatechange = function () {
                    // Check if the request is complete and successful
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const response = JSON.parse(xhr.responseText); // Parse JSON response
                        const suggestions = document.getElementById("suggestions");
                        suggestions.innerHTML = ""; // Clear previous suggestions

                        // Populate suggestions dropdown with results
                        response.forEach(function (patient) {
                            const div = document.createElement("div");
                            div.textContent = patient.name; // Display patient name
                            div.dataset.id = patient.id; // Store patient ID in a data attribute
                            // Set patient ID and name in the form when a suggestion is clicked
                            div.onclick = function () {
                                document.getElementById("patientID").value = patient.id;
                                document.getElementById("patientSearch").value = patient.name;
                                suggestions.innerHTML = ""; // Clear suggestions
                            };
                            suggestions.appendChild(div); // Add suggestion to the dropdown
                        });
                    }
                };
                xhr.send(); // Send the request
            } else {
                document.getElementById("suggestions").innerHTML = ""; // Clear suggestions if query is too short
            }
        }

        // Function to validate the form before submission
        function validateForm() {
            const patientID = document.getElementById("patientID").value;
            // Ensure a valid patient is selected from the suggestions
            if (!patientID) {
                alert("Please select a valid patient from the suggestions.");
                return false; // Prevent form submission
            }
            return true; // Allow form submission
        }
    </script>
    <style>
        /* Styling for the suggestions dropdown */
        #suggestions {
            border: 1px solid #ccc; /* Add a border to suggestions box */
            max-height: 200px; /* Limit the height of the suggestions box */
            overflow-y: auto; /* Enable scrolling if content overflows */
            position: absolute; /* Position it absolutely for proper placement */
            background-color: white; /* White background for readability */
            z-index: 1000; /* Ensure it appears above other elements */
        }

        /* Styling for each suggestion item */
        #suggestions div {
            padding: 5px; /* Add padding for better spacing */
            cursor: pointer; /* Indicate clickable items */
        }

        /* Highlight suggestion on hover */
        #suggestions div:hover {
            background-color: #f0f0f0; /* Light gray background on hover */
        }
    </style>
</head>
<body>
    <h1>File an Insurance Claim</h1>
    <!-- Form for filing an insurance claim -->
    <form action="<%=request.getContextPath()%>/insurance" method="post" onsubmit="return validateForm()">
        <!-- Patient search field -->
        <label for="patientSearch">Patient Name:</label>
        <input type="text" id="patientSearch" name="patientSearch" onkeyup="searchPatient()" placeholder="Type patient name..." required>
        <!-- Hidden field to store selected patient ID -->
        <input type="hidden" id="patientID" name="patientId">
        <!-- Dropdown for displaying search suggestions -->
        <div id="suggestions"></div>
        <br><br>

        <!-- Input field for insurance provider -->
        <label for="insuranceProvider">Insurance Provider:</label>
        <input type="text" id="insuranceProvider" name="insuranceProvider" required><br><br>

        <!-- Input field for claim amount -->
        <label for="claimAmount">Claim Amount:</label>
        <input type="number" step="0.01" id="claimAmount" name="claimAmount" required><br><br>

        <!-- Submit button -->
        <button type="submit" name="action" value="file">File Claim</button>
    </form>
    
    <!-- Button to return to previous menu -->
    <form action="InsuranceMain.jsp" method="get">
        <button type="submit">Back to Main</button>
    </form>
</body>
</html>




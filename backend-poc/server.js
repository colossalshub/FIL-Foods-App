const express = require('express');
const app = express();

// Mock database storing dietary profiles (Asset A-02)
const mockDb = {
  1042: { client_id: 1042, dietary_restrictions: "Low sodium", food_allergies: "Peanuts" },
  55:   { client_id: 55,   dietary_restrictions: "None",       food_allergies: "None" }
};

// Mock authentication middleware (simulating a verified JWT for client_id 55)
const authenticate = (req, res, next) => {
  req.user = { id: 55 }; // Authenticated as client_id = 55
  next();
};

// --- VULNERABLE ENDPOINT (Task 2 Step 1) ---
// Accepts clientId from path parameter without validating ownership
app.get('/vulnerable/dietary-profile/:clientId', authenticate, (req, res) => {
  const { clientId } = req.params;
  const profile = mockDb[clientId];
  if (!profile) return res.status(404).json({ error: "Profile not found" });
  return res.json(profile);
});

// --- HARDENED ENDPOINT (Task 2 Step 3) ---
// Derives identity strictly from req.user.id claim
app.get('/hardened/dietary-profile', authenticate, (req, res) => {
  const userId = req.user.id; // Trusted claim
  const profile = mockDb[userId];
  if (!profile) return res.status(404).json({ error: "Profile not found" });
  return res.json(profile);
});

app.listen(3000, () => console.log('Mock server running on port 3000'));
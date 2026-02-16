<template>
  <div class="customers">
    <h2>Customers</h2>

    <div v-if="loading">Loading customers...</div>
    <div v-else-if="error" class="error">Error: {{ error }}</div>
    <ul v-else>
      <li v-for="c in customers" :key="c.id ?? c.name">
        <strong>{{ c.id }}</strong> — {{ c.name }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

type Customer = {
  id: number | null
  name: string
}

const customers = ref<Customer[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

onMounted(async () => {
  try {
    const res = await fetch('/api/customers')
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`)
    const data = await res.json()
    // Optionally map/validate data
    customers.value = Array.isArray(data) ? data : []
  } catch (e: any) {
    error.value = e?.message ?? String(e)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.customers { margin: 1rem 0; }
.error { color: #b00020; }
ul { list-style: none; padding: 0; }
li { padding: 0.25rem 0; }
</style>

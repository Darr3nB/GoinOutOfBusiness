import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

const apiBaseUrl = 'http://localhost:8080';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/user': apiBaseUrl,
      '/products': apiBaseUrl,
    }
  }
})

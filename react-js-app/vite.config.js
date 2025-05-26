import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import tailwindcss from '@tailwindcss/vite';
import { fileURLToPath, URL } from 'url';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {port: 3000},
  resolve: {
    alias: {
      // alias “@” tới src
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      'assets': fileURLToPath(new URL('./src/assets', import.meta.url)),
      'pages': fileURLToPath(new URL('./src/pages', import.meta.url)),
      'components': fileURLToPath(new URL('./src/components', import.meta.url)),
      'services': fileURLToPath(new URL('./src/services', import.meta.url)),
      'context': fileURLToPath(new URL('./src/context', import.meta.url))
    },
  }
})

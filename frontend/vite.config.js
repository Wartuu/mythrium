import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { splitVendorChunkPlugin } from 'vite';

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react(), splitVendorChunkPlugin()],
    build: {
        chunkSizeWarningLimit: 512,

        rollupOptions: {
            output: {
                manualChunks(id) {
                    if (id.includes('@open-ish')) {
                        return '@open-ish';
                    }

                    if (
                        id.includes('react-router-dom') ||
                        id.includes('react-router') ||
                        id.includes('@remix-run')
                    ) {
                        return '@react-router';
                    } else if (
                        id.includes('react-spring')
                    ) {
                        return '@react-spring';
                    }
                }
            }
        }
    }
});

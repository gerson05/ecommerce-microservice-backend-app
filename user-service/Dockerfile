
FROM nginx:alpine
RUN echo 'server { listen 8700; location / { return 200 "User Service is running!"; add_header Content-Type text/plain; } }' > /etc/nginx/conf.d/default.conf
EXPOSE 8700
CMD ["nginx", "-g", "daemon off;"]



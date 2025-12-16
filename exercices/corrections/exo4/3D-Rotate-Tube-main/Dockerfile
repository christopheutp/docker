FROM nginx

WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

COPY . .

EXPOSE 80

CMD ["nginx","-g","daemon off;"]

# docker build -t rotate .
# docker run -p 8085:80 -d rotate


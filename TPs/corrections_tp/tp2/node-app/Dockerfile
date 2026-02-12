ARG NODE_VERSION=18
FROM node:${NODE_VERSION}-alpine

WORKDIR /app

COPY package.json ./
RUN npm install --only=production

COPY app.js .

RUN addgroup -g 1001 appgroup \
 && adduser -D -u 1001 -G appgroup appuser

USER appuser

ENV APP_ENV=production
ENV PORT=3000

EXPOSE 3000

CMD ["npm", "start"]

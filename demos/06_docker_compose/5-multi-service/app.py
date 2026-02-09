from flask import Flask, jsonify
import redis
import psycopg2
import os

app = Flask(__name__)

# Connexions
redis_client = redis.from_url(os.environ.get('REDIS_URL', 'redis://localhost:6379'))

def get_db():
    return psycopg2.connect(os.environ.get('DATABASE_URL'))

@app.route('/')
def index():
    # Incrémenter le compteur Redis
    visits = redis_client.incr('visits')
    return jsonify({
        'message': 'Hello Docker Compose!',
        'visits': visits
    })

@app.route('/health')
def health():
    status = {'app': 'ok'}
    
    # Test Redis
    try:
        redis_client.ping()
        status['redis'] = 'ok'
    except:
        status['redis'] = 'error'
    
    # Test PostgreSQL
    try:
        conn = get_db()
        conn.close()
        status['postgres'] = 'ok'
    except:
        status['postgres'] = 'error'
    
    return jsonify(status)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

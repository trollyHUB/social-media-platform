/**
 * Social Media Platform - API Client
 * Используется для взаимодействия с REST API
 */
const SocialMediaAPI = {
    baseUrl: '',

    async request(method, url, body = null) {
        const options = {
            method,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        if (body) options.body = JSON.stringify(body);

        const response = await fetch(this.baseUrl + url, options);
        return {
            status: response.status,
            ok: response.ok,
            data: await response.json()
        };
    },

    // Posts
    getAllPosts: function() { return this.request('GET', '/api/posts'); },
    getPostById: function(id) { return this.request('GET', '/api/posts/' + id); },
    getPostsByAuthor: function(author) { return this.request('GET', '/api/posts?author=' + author); },
    createPost: function(author, content) { return this.request('POST', '/api/posts', {author, content}); },
    updatePost: function(id, author, content) { return this.request('PUT', '/api/posts/' + id, {author, content}); },
    deletePost: function(id) { return this.request('DELETE', '/api/posts/' + id); },
    likePost: function(id) { return this.request('POST', '/api/posts/' + id + '/like'); },

    // Users
    getAllUsers: function() { return this.request('GET', '/api/users'); },
    getUserById: function(id) { return this.request('GET', '/api/users/' + id); },
    createUser: function(username, email, bio) { return this.request('POST', '/api/users', {username, email, bio}); },
    deleteUser: function(id) { return this.request('DELETE', '/api/users/' + id); },

    // Comments
    getComments: function(postId) { return this.request('GET', '/api/posts/' + postId + '/comments'); },
    addComment: function(postId, author, content) { return this.request('POST', '/api/posts/' + postId + '/comments', {author, content}); },
    deleteComment: function(id) { return this.request('DELETE', '/api/comments/' + id); },

    // Stats
    getStats: function() { return this.request('GET', '/api/stats'); },
    healthCheck: function() { return this.request('GET', '/api/health'); }
};

console.log('📡 SocialMediaAPI client loaded');

import {
  del, get, patch, post,
} from '../util/http';

export function exists(key) {
  return get(`/section/${key}/exists`, {});
}

export function inspect(key) {
  return get(`/section/${key}`, {});
}

export function insert(key, name, remark) {
  let finalKey = { long_id: key };
  if (key === '') {
    finalKey = null;
  }
  return post('/section', {
    key: finalKey,
    remark,
    name,
  });
}

export function remove(key) {
  return del(`/section/${key}`, {});
}

export function update(key, name, remark) {
  return patch('/section', {
    key: {
      long_id: key,
    },
    remark,
    name,
  });
}

export function all(page, rows) {
  return get('/section/all', {
    page,
    rows,
  });
}

export function nameLike(pattern, page, rows) {
  return get('/section/name-like', {
    pattern,
    page,
    rows,
  });
}

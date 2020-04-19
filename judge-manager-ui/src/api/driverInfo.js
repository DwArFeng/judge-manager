import {
  del, get, patch, post,
} from '../util/http';

export function exists(key) {
  return get(`/driver-info/${key}/exists`, {});
}

export function inspect(key) {
  return get(`/driver-info/${key}`, {});
}

export function insert(key, sectionkey, enabled, remark, content, type) {
  let finalKey = { long_id: key };
  if (key === '') {
    finalKey = null;
  }
  return post('/driver-info', {
    key: finalKey,
    sectionKey: {
      long_id: sectionkey,
    },
    enabled,
    remark,
    content,
    type,
  });
}

export function remove(key) {
  return del(`/driver-info/${key}`, {});
}

export function update(key, sectionkey, enabled, remark, content, type) {
  return patch('/driver-info', {
    key: {
      long_id: key,
    },
    sectionKey: {
      long_id: sectionkey,
    },
    enabled,
    remark,
    content,
    type,
  });
}

export function all(page, rows) {
  return get('/driver-info/all', {
    page,
    rows,
  });
}

export function childForSection(key, page, rows) {
  return get(`/section/${key}/driver-infos`, {
    key,
    page,
    rows,
  });
}

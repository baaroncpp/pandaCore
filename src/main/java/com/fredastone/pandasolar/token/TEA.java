package com.fredastone.pandasolar.token;

/** 
 * Description 
 *
 * @author LiuQiao
 * @Date Dec 14, 2018 10:54:12 AM 
 */

/**
 * @author: heiing 2013-01-20 01:20
 */
public class TEA {
 
	public TEA(byte[] k) throws Exception {
		
		 if (k.length != 16) {
	            throw new Exception("Key Length Not Supported");
	        }
	        k0 = bytes_to_uint32(new byte[] {k[0], k[1], k[2], k[3]});
	        k1 = bytes_to_uint32(new byte[] {k[4], k[5], k[6], k[7]});
	        k2 = bytes_to_uint32(new byte[] {k[8], k[9], k[10], k[11]});
	        k3 = bytes_to_uint32(new byte[] {k[12], k[13], k[14], k[15]});
	}
    /**
     * 设置加密的轮数，默认为32轮
     * @param loops 加密轮数
     * @return 轮数为16、32、64时，返回true，否则返回false
     */
    public boolean setLoops(int loops) {
        switch (loops) {
            case 16:
            case 32:
            case 64:
                this.loops = loops;
                return true;
        }
        return false;
    }
    
    private static long UINT32_MAX = 0xFFFFFFFFL;
    private static long BYTE_1 = 0xFFL;
    private static long BYTE_2 = 0xFF00L;
    private static long BYTE_3 = 0xFF0000L;
    private static long BYTE_4 = 0xFF000000L;
    
    private static long delta = 0x9E3779B9L;
    
    private final long k0, k1, k2, k3;
    
    private int loops = 32;
    
    /**
     * 加密一组明文
     * @param v 需要加密的明文
     * @return 返回密文
     */
    public byte[] encrypt_group(byte[] v) {
        long v0 = bytes_to_uint32(new byte[] {v[0], v[1], v[2], v[3]});
        long v1 = bytes_to_uint32(new byte[] {v[4], v[5], v[6], v[7]});
        long sum = 0L;
        long v0_xor_1 = 0L, v0_xor_2 = 0L, v0_xor_3 = 0L;
        long v1_xor_1 = 0L, v1_xor_2 = 0L, v1_xor_3 = 0L;
        for (int i = 0; i < loops; i++) {
            sum = toUInt32(sum + delta);
            v0_xor_1 = toUInt32(toUInt32(v1 << 4) + k0);
            v0_xor_2 = toUInt32(v1 + sum);
            v0_xor_3 = toUInt32((v1 >> 5) + k1);
            v0 = toUInt32(  v0 + toUInt32(v0_xor_1 ^ v0_xor_2 ^ v0_xor_3)  );
            v1_xor_1 = toUInt32(toUInt32(v0 << 4) + k2);
            v1_xor_2 = toUInt32(v0 + sum);
            v1_xor_3 = toUInt32((v0 >> 5) + k3);
        //    System.out.printf("%08X\t%08X\t%08X\t%08X\n", i, v0, v0 >> 5, k3);
            v1 = toUInt32(  v1 + toUInt32(v1_xor_1 ^ v1_xor_2 ^ v1_xor_3)  );
        }
        byte[] b0 = long_to_bytes(v0, 4);
        byte[] b1 = long_to_bytes(v1, 4);
        return new byte[] {b0[0], b0[1], b0[2], b0[3], b1[0], b1[1], b1[2], b1[3]};
    }
    
    /**
     * 解密一组密文
     * @param v 要解密的密文
     * @return 返回明文
     */
    public byte[] decrypt_group(byte[] v) {
        long v0 = bytes_to_uint32(new byte[] {v[0], v[1], v[2], v[3]});
        long v1 = bytes_to_uint32(new byte[] {v[4], v[5], v[6], v[7]});
        long sum = 0xC6EF3720L, tmp = 0L;
        for (int i = 0; i < loops; i++) {
            tmp = toUInt32(toUInt32(v0 << 4) + k2);
            v1 = toUInt32(  v1 - toUInt32(tmp ^  toUInt32(v0 + sum) ^ toUInt32((v0 >> 5) + k3))  );
            tmp = toUInt32(toUInt32(v1 << 4) + k0);
            v0 = toUInt32(  v0 - toUInt32(tmp ^  toUInt32(v1 + sum) ^ toUInt32((v1 >> 5) + k1))  );
            sum = toUInt32(sum - delta);
        }
        byte[] b0 = long_to_bytes(v0, 4);
        byte[] b1 = long_to_bytes(v1, 4);
        return new byte[] {b0[0], b0[1], b0[2], b0[3], b1[0], b1[1], b1[2], b1[3]};
    }
    
    
    /**
     * 将 long 类型的 n 转为 byte 数组，如果 len 为 4，则只返回低32位的4个byte
     * @param n 需要转换的long
     * @param len 若为4，则只返回低32位的4个byte，否则返回8个byte
     * @return 转换后byte数组
     */
    private static byte[] long_to_bytes(long n, int len) {
        byte a = (byte)((n & BYTE_4) >> 24);
        byte b = (byte)((n & BYTE_3) >> 16);
        byte c = (byte)((n & BYTE_2) >> 8);
        byte d = (byte)(n & BYTE_1);
        if (len == 4) {
            return new byte[] {a, b, c, d};
        }
        byte ha = (byte)(n >> 56);
        byte hb = (byte)((n >> 48) & BYTE_1);
        byte hc = (byte)((n >> 40) & BYTE_1);
        byte hd = (byte)((n >> 32) & BYTE_1);
        return new byte[] {ha, hb, hc, hd, a, b, c, d};
    }
    
    /**
     * 将4个byte转为 Unsigned Integer 32，以 long 形式返回
     * @param bs 需要转换的字节
     * @return 返回 long，高32位为0，低32位视为Unsigned Integer
     */
    private static long bytes_to_uint32(byte[] bs) {
        return ((bs[0]<<24) & BYTE_4) +
               ((bs[1]<<16) & BYTE_3) +
               ((bs[2]<<8)  & BYTE_2) +
               (bs[3] & BYTE_1);
    }
    
    /**
     * 将long的高32位清除，只保留低32位，低32位视为Unsigned Integer
     * @param n 需要清除的long
     * @return 返回高32位全为0的long
     */
    private static long toUInt32(long n) {
        return n & UINT32_MAX;
    }
    
    
    // -------------------------------------------------------
    // 以下 是用于Debug的函数
    // -------------------------------------------------------
    private static void println_array(byte[] b) {
        for (byte x : b) {
            System.out.printf("%02X ", x);
        }
        System.out.println();
    }
    /*private static void println_array(long[] b) {
        for (long x : b) {
            System.out.printf("%016X ", x);
        }
        System.out.println();
    }*/
    
    
    
    public static void main(String[] args) {
     
    	
        byte[] pnt = "2d0101206101010000006".getBytes();
        final byte[] key = new byte[] {
        		0x78, (byte) 0x9f,0x56,0x45,
        		(byte) 0xf6,(byte) 0x8b,(byte) 0xd5,(byte) 0xa4, 
        		(byte) 0x81,(byte) 0x96,0x3f,(byte) 0xfa,
        		0x45,(byte) 0x8f,(byte) 0xac,0x58
                };
        
        TEA t = null;
		try {
			t = new TEA(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
//        byte[] enc = t.encrypt(v, k);
//        byte[] dec = t.decrypt(enc, k);
        byte[] enc = t.encrypt_group(pnt);
        //byte[] enc = new byte[] {(byte) 0xC1, (byte) 0xC6, 0x48, 0x7A, (byte) 0x9E, 0x6F, (byte) 0xF2, 0x56};
        byte[] dec = t.decrypt_group(enc);
        
        
        System.out.println("Key:");
        println_array(key);
        
        System.out.println("Encrypt And Decrypt:");
        println_array(pnt);
        println_array(enc);
        println_array(dec);
    }
    
}

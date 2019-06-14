package mods.skipsign;

public class Key
{
    public Object Value;
    public Object OldValue;
    public String CfgName;
    public String CfgType;
    
    public Key(Object val, String name, String type)
    {
        this.Value = val;
        this.CfgName = name;
        this.CfgType = type;
    }
    
    public void Value(Object v)
    {
        this.Value = v;
        this.OldValue = v;
    }
    
    public String Cfg()
    {
        this.OldValue = this.Value;
        
        if (this.CfgType == "I")
        {
            String s =  String.format("%s:%s=%d", this.CfgType, this.CfgName, this.Int());
            //FMLLog.info(s);
            return s;
        }
        
        if (this.CfgType == "B")
        {
            return String.format("%s:%s=%s", this.CfgType, this.CfgName, this.Bool());
        }

        return "";
        
    }
    
    public void Oldish()
    {
        this.OldValue = this.Value;
    }
    
    public String OldCfg()
    {
        if (this.CfgType == "I")
        {
            String s =  String.format("%s:%s=%d", this.CfgType, this.CfgName, this.OInt());
            //FMLLog.info(s);
            return s;
        }
        
        if (this.CfgType == "B")
        {
            return String.format("%s:%s=%s", this.CfgType, this.CfgName, this.OBool());
        }

        return "";
        
    }
    
    public float Float()
    {
        return new Float(this.Value.toString());
    }
    
    public boolean Bool()
    {
        return new Boolean(this.Value.toString());
    }
    
    public int Int()
    {
        return new Integer(this.Value.toString());
    }
    
    private int OInt()
    {
        return new Integer(this.OldValue.toString());
    }
    
    private boolean OBool()
    {
        return new Boolean(this.OldValue.toString());
    }
}

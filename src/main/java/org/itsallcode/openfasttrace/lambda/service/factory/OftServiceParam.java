package org.itsallcode.openfasttrace.lambda.service.factory;

import com.github.kaklakariada.aws.lambda.service.ServiceParams;

public class OftServiceParam implements ServiceParams
{
    private final String stage;

    public OftServiceParam(String stage)
    {
        this.stage = stage;
    }

    public String getStage()
    {
        return stage;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stage == null) ? 0 : stage.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final OftServiceParam other = (OftServiceParam) obj;
        if (stage == null)
        {
            if (other.stage != null)
            {
                return false;
            }
        }
        else if (!stage.equals(other.stage))
        {
            return false;
        }
        return true;
    }
}
